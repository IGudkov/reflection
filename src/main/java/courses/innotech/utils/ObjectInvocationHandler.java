package courses.innotech.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ObjectInvocationHandler implements InvocationHandler {
  private Object obj;

  private Map<String,Object> actions = new HashMap<>();
  public ObjectInvocationHandler(Object obj) {
    this.obj = obj;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

    Method methodObject = obj.getClass().getDeclaredMethod(method.getName(),method.getParameterTypes());
    String methodName = methodObject.getName();

    if (methodObject.isAnnotationPresent(Mutator.class)) {
      actions.clear();
      }

    if (methodObject.isAnnotationPresent(Cache.class))
      if (actions.containsKey(methodName)) {
        return actions.get(methodName);
      }
      else {
        Object returnMethodValue = method.invoke(obj, args);
        actions.put(methodName, returnMethodValue);
        return returnMethodValue;
      }

    return method.invoke(obj, args);
  }
}
