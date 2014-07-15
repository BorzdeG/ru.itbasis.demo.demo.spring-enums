package ru.itbasis.demo.spring.enums;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

@Component
public class EnumHandlerBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
	private static final transient Logger LOG = LoggerFactory.getLogger(EnumHandlerBeanFactoryPostProcessor.class.getName());

	@Override
	public void postProcessBeanFactory(final ConfigurableListableBeanFactory beanFactory) throws BeansException {
		final String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();

		for (String beanDefinitionName : beanDefinitionNames) {
			final BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
			LOG.debug("beanDefinitionName: {}", beanDefinitionName);

			final String beanClassName = beanDefinition.getBeanClassName();
			LOG.debug("beanClassName: {}", beanClassName);

			try {
				final Class<?> beanClass = Class.forName(beanClassName);
				if (beanClass.isEnum()) {

					LOG.trace("found ENUM class: {}", beanClass);
					LOG.trace("interfaces: {}", beanClass.getInterfaces());

					beanDefinition.setFactoryMethodName("values");
				}

			} catch (ClassNotFoundException e) {
				LOG.error(e.getMessage(), e);
			}
		}
	}
}
