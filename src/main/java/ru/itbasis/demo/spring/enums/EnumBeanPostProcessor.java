package ru.itbasis.demo.spring.enums;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class EnumBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware {
	private static final transient Logger LOG = LoggerFactory.getLogger(EnumBeanPostProcessor.class.getName());

	private ApplicationContext context;

	private Set<IEnum> getEnums() {
		final Map<String, Object> enumMap = context.getBeansWithAnnotation(EnumAnnotation.class);
		LOG.debug("enumMap.size: {}", enumMap.size());

		Set<IEnum> result = new HashSet<IEnum>();
		for (Object o : enumMap.values()) {

			if (o.getClass().isArray()) {
				final IEnum[] o1 = (IEnum[]) o;
				Collections.addAll(result, o1);

			} else {
				result.add((IEnum) o);

			}
		}

		LOG.debug("result: {}", result);
		return result;
	}

	@SuppressWarnings("unchecked")
	private boolean isAutowiredEnumSetField(Field field) {
		if (!AnnotatedElementUtils.isAnnotated(field, Autowired.class.getName())) {
			return false;
		}

		final Class<?> fieldType = field.getType();

		if (!Set.class.isAssignableFrom(fieldType)) {
			return false;
		}

		final ParameterizedType type = (ParameterizedType) field.getGenericType();
		final Type[] typeArguments = type.getActualTypeArguments();

		final Class<? extends Type> aClass = (Class<? extends Type>) typeArguments[0];

		return aClass.isAssignableFrom(IEnum.class);
	}

	@Override
	public Object postProcessBeforeInitialization(final Object bean, final String beanName) throws BeansException {
		LOG.debug("bean: {}", bean);
		LOG.debug("beanName: {}", beanName);

		final Set<IEnum> enums = getEnums();
		if (enums.size() < 1) {
			return bean;
		}

		final Class<?> beanClass = bean.getClass();
		final Field[] fields = beanClass.getDeclaredFields();

		for (Field field : fields) {

			if (isAutowiredEnumSetField(field)) {
				LOG.trace("field inject values.");
				field.setAccessible(true);
				ReflectionUtils.setField(field, bean, enums);
			}
		}

		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
		return bean;
	}

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}
}
