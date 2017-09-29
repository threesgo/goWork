if ($.fn.validatebox.defaults.rules){
	var rules=$.fn.validatebox.defaults.rules;
	if(rules.illegal){
		rules.illegal.message="输入内容出现非法字符.";
	}
	
	if(rules.equals){
		rules.equals.message="字段不匹配.";
	}
	
	if(rules.phone){
		rules.phone.message="手机号码格式不正确.";
	}
	
	if(rules.between){
		rules.between.message="数字应在 {0}与{1}之间";
	}
	
	if(rules.less){
		rules.less.message="数字应大于{0}";
	}
	
	if(rules.greater){
		rules.greater.message="数字应小于{0}";
	}
	
	if(rules.min){
		rules.min.message="输入内容长度不能低于 {0}.";
	}
	
	if(rules.max){
		rules.max.message="输入内容长度不能超过 {0}.";
	}
}
