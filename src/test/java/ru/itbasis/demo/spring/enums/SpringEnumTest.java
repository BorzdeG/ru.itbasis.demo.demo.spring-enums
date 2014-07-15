package ru.itbasis.demo.spring.enums;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Set;

@ContextConfiguration(locations = {"classpath:demo.xml"})
public class SpringEnumTest extends AbstractTestNGSpringContextTests {
	private static final transient Logger LOG = LoggerFactory.getLogger(SpringEnumTest.class.getName());

	@Autowired
	private SpringEnum springEnum;

	@Test
	public void testGetFieldEnumSet() throws Exception {
		final Set<IEnum> fieldEnum = springEnum.getFieldEnum();
		LOG.debug("fieldEnum: {}", fieldEnum);
		Assert.assertNotNull(fieldEnum);
		Assert.assertEquals(fieldEnum.size(), 2);
	}
}