package com.huike.clues.service.impl;

import java.util.*;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huike.clues.domain.SysMenu;
import com.huike.clues.domain.dto.SysMenuConditionVO;
import com.huike.clues.result.RoleMenuTreeAjaxResult;
import com.huike.common.core.domain.TreeSelect;
import org.springframework.stereotype.Service;
import com.huike.common.constant.UserConstants;
import com.huike.common.core.domain.entity.SysMenuDTO;
import com.huike.common.utils.SecurityUtils;
import com.huike.common.utils.StringUtils;
import com.huike.clues.domain.vo.MetaVo;
import com.huike.clues.domain.vo.RouterVo;
import com.huike.clues.mapper.SysMenuMapper;
import com.huike.clues.service.ISysMenuService;

import javax.annotation.Resource;

/**
 * 菜单 业务层处理
 *
 *
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService
{
    public static final String PREMISSION_STRING = "perms[\"{0}\"]";

    @Resource
    private SysMenuMapper menuMapper;



    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectMenuPermsByUserId(Long userId)
    {
        List<String> perms = menuMapper.selectMenuPermsByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms)
        {
            if (StringUtils.isNotEmpty(perm))
            {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 根据用户ID查询菜单
     *
     * @param userId 用户名称
     * @return 菜单列表
     */
    @Override
    public List<SysMenuDTO> selectMenuTreeByUserId(Long userId)
    {
        List<SysMenuDTO> menus = null;
        if (SecurityUtils.isAdmin(userId))
        {
            menus = menuMapper.selectMenuTreeAll();
        }
        else
        {
            menus = menuMapper.selectMenuTreeByUserId(userId);
        }
        return getChildPerms(menus, 0);
    }



    /**
     * 构建前端路由所需要的菜单
     *
     * @param menus 菜单列表
     * @return 路由列表
     */
    @Override
    public List<RouterVo> buildMenus(List<SysMenuDTO> menus)
    {
        List<RouterVo> routers = new LinkedList<RouterVo>();
        for (SysMenuDTO menu : menus)
        {
            RouterVo router = new RouterVo();
            router.setHidden("1".equals(menu.getVisible()));
            router.setName(getRouteName(menu));
            router.setPath(getRouterPath(menu));
            router.setComponent(getComponent(menu));
            router.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon(), StringUtils.equals("1", menu.getIsCache())));
            List<SysMenuDTO> cMenus = menu.getChildren();
            if (!cMenus.isEmpty() && cMenus.size() > 0 && UserConstants.TYPE_DIR.equals(menu.getMenuType()))
            {
                router.setAlwaysShow(true);
                router.setRedirect("noRedirect");
                router.setChildren(buildMenus(cMenus));
            }
            else if (isMeunFrame(menu))
            {
                List<RouterVo> childrenList = new ArrayList<RouterVo>();
                RouterVo children = new RouterVo();
                children.setPath(menu.getPath());
                children.setComponent(menu.getComponent());
                children.setName(StringUtils.capitalize(menu.getPath()));
                children.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon(), StringUtils.equals("1", menu.getIsCache())));
                childrenList.add(children);
                router.setChildren(childrenList);
            }
            routers.add(router);
        }
        return routers;
    }










    /**
     * 获取路由名称
     *
     * @param menu 菜单信息
     * @return 路由名称
     */
    public String getRouteName(SysMenuDTO menu)
    {
        String routerName = StringUtils.capitalize(menu.getPath());
        // 非外链并且是一级目录（类型为目录）
        if (isMeunFrame(menu))
        {
            routerName = StringUtils.EMPTY;
        }
        return routerName;
    }

    /**
     * 获取路由地址
     *
     * @param menu 菜单信息
     * @return 路由地址
     */
    public String getRouterPath(SysMenuDTO menu)
    {
        String routerPath = menu.getPath();
        // 非外链并且是一级目录（类型为目录）
        if (0 == menu.getParentId().intValue() && UserConstants.TYPE_DIR.equals(menu.getMenuType())
                && UserConstants.NO_FRAME.equals(menu.getIsFrame()))
        {
            routerPath = "/" + menu.getPath();
        }
        // 非外链并且是一级目录（类型为菜单）
        else if (isMeunFrame(menu))
        {
            routerPath = "/";
        }
        return routerPath;
    }

    /**
     * 获取组件信息
     *
     * @param menu 菜单信息
     * @return 组件信息
     */
    public String getComponent(SysMenuDTO menu)
    {
        String component = UserConstants.LAYOUT;
        if (StringUtils.isNotEmpty(menu.getComponent()) && !isMeunFrame(menu))
        {
            component = menu.getComponent();
        }
        else if (StringUtils.isEmpty(menu.getComponent()) && isParentView(menu))
        {
            component = UserConstants.PARENT_VIEW;
        }
        return component;
    }

    /**
     * 是否为菜单内部跳转
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isMeunFrame(SysMenuDTO menu)
    {
        return menu.getParentId().intValue() == 0 && UserConstants.TYPE_MENU.equals(menu.getMenuType())
                && menu.getIsFrame().equals(UserConstants.NO_FRAME);
    }

    /**
     * 是否为parent_view组件
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isParentView(SysMenuDTO menu)
    {
        return menu.getParentId().intValue() != 0 && UserConstants.TYPE_DIR.equals(menu.getMenuType());
    }

    /**
     * 递归列表
     *
     * @param list
     * @param t
     */
    private void recursionFn(List<SysMenuDTO> list, SysMenuDTO t)
    {
        // 得到子节点列表
        List<SysMenuDTO> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysMenuDTO tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                recursionFn(list, tChild);
            }
        }
    }
    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param list 分类表
     * @param parentId 传入的父节点ID
     * @return String
     */
    public List<SysMenuDTO> getChildPerms(List<SysMenuDTO> list, int parentId)
    {
        List<SysMenuDTO> returnList = new ArrayList<SysMenuDTO>();
        for (Iterator<SysMenuDTO> iterator = list.iterator(); iterator.hasNext();)
        {
            SysMenuDTO t = (SysMenuDTO) iterator.next();
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (t.getParentId() == parentId)
            {
                recursionFn(list, t);
                returnList.add(t);
            }
        }
        return returnList;
    }



    /**
     * 得到子节点列表
     */
    private List<SysMenuDTO> getChildList(List<SysMenuDTO> list, SysMenuDTO t)
    {
        List<SysMenuDTO> tlist = new ArrayList<SysMenuDTO>();
        Iterator<SysMenuDTO> it = list.iterator();
        while (it.hasNext())
        {
            SysMenuDTO n = (SysMenuDTO) it.next();
            if (n.getParentId().longValue() == t.getMenuId().longValue())
            {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysMenuDTO> list, SysMenuDTO t)
    {
        return getChildList(list, t).size() > 0 ? true : false;
    }

    /**
     * 根据菜单编号获取详细信息
     *
     * @param menuId
     * @return
     */
    @Override
    public SysMenu selectMenuByMenuId(Integer menuId) {
        return menuMapper.selectById(menuId);
    }

    /**
     * 获取菜单列表
     *
     * @param sysMenuConditionVO
     * @return
     */
    @Override
    public List<SysMenu> selectByCondition(SysMenuConditionVO sysMenuConditionVO) {
        // 创建 QueryWrapper 对象，设置多个查询条件 //menuName=&status=
       /* QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(sysMenuConditionVO.getMenuName()!=null,"menu_name", sysMenuConditionVO.getMenuName())
                .eq(sysMenuConditionVO.getMenuName()!=null,"status",sysMenuConditionVO.getStatus());
*/
        // 调用 selectList 方法进行查询
        List<SysMenu> userList = menuMapper.selectList(sysMenuConditionVO);
        return userList;
    }




    /**
     * 获取菜单下拉树列表接口
     *
     * @param
     * @return
     * 实现思路：
     * 先查出所有parentId=0的menu，收集成集合<TreeSelect>(需要过滤数据)，定义一个递归方法，参数是menu_id
     * 遍历集合，每次传入一个parentId=0的menu_id,这个递归方法的目的是获取该顶级父目录下的所有目录，通过
     * 递归，返回一个List<TreeSelect>,遍历结束后，可以得到所有顶级父目录的List<TreeSelect>，封装后返回
     * 到Controller
     */
    public List<TreeSelect> recursionMenu(Long menuId){
        List<TreeSelect> treeSelectList=new ArrayList<>();
        //1.获取menu_id=menuId的所有menu，收集成集合
         treeSelectList = menuMapper.getMenuIdAndMenuName(menuId.intValue());
        //如果集合的长度=0，返回(递归结束条件)
        if(treeSelectList.size()==0){
            return treeSelectList;
        }
        //2.遍历该收集的集合，循环递归for(元素:集合){recursionMenu()}
        for (TreeSelect treeSelect : treeSelectList) {
            List<TreeSelect> treeSelectList1 = recursionMenu((Long) treeSelect.getId());
            treeSelect.setChildren(treeSelectList1);
        }
        return treeSelectList;
        //最后，将集合返回，到主调函数，封装到根目录的children下
    }
    @Override
    public List<TreeSelect> selectMenuTree() {
        int flag=0;
        List<TreeSelect> treeSelectList=new ArrayList<>();
        //1.所有parentId=0的menu，收集成集合List<TreeSelect>(需要过滤数据,是否显示?菜单状态?)
        List<TreeSelect> sysMenuList=menuMapper.getMenuIdAndMenuName(0);

        //定义一个递归方法，参数是menu_id
        // 遍历集合，每次传入一个parentId=0的menu_id
        if(sysMenuList.isEmpty()||sysMenuList==null){
            return null;
        }
        for (TreeSelect treeSelect : sysMenuList) {
            List<TreeSelect> treeSelectList1 = recursionMenu((Long)treeSelect.getId());
            treeSelect.setChildren(treeSelectList1);
        }

    return sysMenuList;
    }

    /**
     * 加载对应角色菜单列表树
     *
     *查询角色对应的菜单树。（角色和菜单一对多的关系 通过中间表关联）,可能有多个菜单树？
     * 记录下每个菜单树的menu_id封装成集合==>checkedKeys
     * 调用selectMenuTree取得菜单树===>menus
     * 最后封装数据返回
     * @param roleId
     */
    @Override
    public RoleMenuTreeAjaxResult roleMenuTreeselect(Integer roleId) {
        //1.查询角色对应的菜单树。（角色和菜单一对多的关系 通过中间表关联）,可能有多个菜单树？
            //1.1连表查询
        List<Integer> menuIds=menuMapper.roleMenuTreeselect(roleId);
        List<Integer> checkedKeys=new ArrayList<>();
        checkedKeys.addAll(menuIds);
        //2.调用selectMenuTree取得菜单树===>menus
        //根据roleId取得menu_list
//        List<SysMenu> sysMenuList=menuMapper.selectMenuListByroleId(roleId);
        //收集成集合List<TreeSelect>(需要过滤数据)
        List<TreeSelect> sysMenuList=menuMapper.selectTreeSelectListByroleId(menuIds);
        List<List<TreeSelect>> arr=new ArrayList<>();
        for (TreeSelect treeSelect : sysMenuList) {
            List<TreeSelect> treeSelectList = recursionMenu(Long.valueOf((Long)treeSelect.getId()));
            treeSelect.setChildren(treeSelectList);
        }

        //最后封装数据返回
        RoleMenuTreeAjaxResult roleMenuTreeAjaxResult = new RoleMenuTreeAjaxResult(200,"操作成功");
        roleMenuTreeAjaxResult.setMenus(sysMenuList);
        roleMenuTreeAjaxResult.setCheckedKeys(checkedKeys);

        return roleMenuTreeAjaxResult;
    }

    /**
     * 修改菜单
     *调用mybatis plus完成修改
     * @param sysMenu
     */
    @Override
    public void updateMenu(SysMenu sysMenu) {
        menuMapper.updateMenu(sysMenu);
    }

    /**
     * 新增菜单
     *
     * @param sysMenu
     */
    @Override
    public void insertMenu(SysMenu sysMenu) {
        menuMapper.insert(sysMenu);
    }

    /**
     * 删除菜单.存在子菜单或已分配,不允许删除
     *
     * @param menuId
     * @return
     */
    @Override
    public void deleteMenu(Integer menuId) {
        //判断是否存在子菜单
//        List<SysMenu> sysMenuList=menuMapper.selectByParentId(menuId);
       /* if(!sysMenuList.isEmpty()){
            return "删除失败";
        }*/
        //判断是否与角色关联(判断role表的menu_check_strictly字段)
            //通过菜单id获取与之关联的所有角色==>集合，然后获取所有角色的menu_check_strictly==>集合menu_check_strictlyArr
                //连表查询
//        Long menu_check_strictlyCount =menuMapper.selectRolemenu_check_strictlyByMenuId(menuId);
            //判断menu_check_strictlyArr是否存在数值为1的(为1表示关联显示菜单树)
        /*if(menu_check_strictlyCount>0){
            return "删除失败";
        }*/

        //空，可以删除
        menuMapper.deleteMenu(menuId);
    }
}
