package ru.itbasis.demo.spring.enums;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SpringEnum {

	@Autowired(required = false)
	private DemoEnum0 fieldEnumSet;

	public DemoEnum0 getFieldEnum() {
		return fieldEnumSet;
	}
}
