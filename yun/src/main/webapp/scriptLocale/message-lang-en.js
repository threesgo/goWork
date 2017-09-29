if ($.fn.validatebox.defaults.rules){
	var rules=$.fn.validatebox.defaults.rules;
	if(rules.illegal){
		rules.illegal.message=" Illegal characters. Please re-type";
	}
	
	if(rules.equals){
		rules.equals.message="Fields do not match.";
	}
	
	if(rules.phone){
		rules.phone.message="Mobile phone number is not in the correct format.";
	}
	
	if(rules.between){
		rules.between.message="Number should be between {0} and {1}";
	}
	
	if(rules.less){
		rules.less.message="The figures should be greater than {0}";
	}
	
	if(rules.greater){
		rules.greater.message="The figures should be less than{0}";
	}
	
	if(rules.min){
		rules.min.message="Input the content length cannot be less than {0}.";
	}
	
	if(rules.max){
		rules.max.message="Input the content length can not be above{0}.";
	}
}