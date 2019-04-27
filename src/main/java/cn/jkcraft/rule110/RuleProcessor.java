package cn.jkcraft.rule110;

/**
 * @author jiangzheng
 */
public interface RuleProcessor<T> {

    T process(T a,T b,T c);

    static <T> RuleProcessor<T> standar(){
        return (a,b,c)->a;
    }

}
