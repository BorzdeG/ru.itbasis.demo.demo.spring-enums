package ru.itbasis.demo.spring.enums;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class SpringEnum {

	@Autowired(required = false)
	private Set<IEnum> fieldEnumSet;

	public Set<IEnum> getFieldEnum() {
		return fieldEnumSet;
	}
}
