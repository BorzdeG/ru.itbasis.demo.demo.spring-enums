package ru.itbasis.demo.spring.enums;

@EnumAnnotation
public class DemoNoEnumClass implements IEnum {
	@Override
	public String name() {
		return "owner class";
	}
}
