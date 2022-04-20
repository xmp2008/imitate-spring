package cn.xmp.spring;

/**
 * @Title:
 * @BelongProjecet imitate-spring
 * @BelongPackage cn.xmp.spring
 * @Description: bean元数据
 * @Author: xmp
 * @Date: 2022/4/19 16:53
 */
public class BeanDefinition {

    private Class type;

    private String scope;

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

}
