package com.neighbor.ServiceAPI.mobile.model;

public enum ResCode {

	/** variable SUCCESS.*/
	SUCCESS(0), FAIL(9999), NO_DATA(99), FAIL2(9990), RCEPT_FAIL(9950), USE_CAR_FAIL(9960), BLACKLIST(9970);
	
	private final Integer code;
	
	ResCode(final Integer pcode){
		this.code = pcode;
	}
	public Integer getCode() {
		return code;
	}
	@Override
	public String toString() {
		
		final int comp0 = 0;
		final int fail99 = 99, fail9990=9990;
		final int fail9950 = 9950;
        final int fail9960 = 9960;
        final int fail9970 = 9970;
		
		switch (code) {
			case comp0:
				return "Complete!";
			case fail99:
				return "no Data";
			case fail9950:
				return "rcept_fail";
            case fail9960:
                return "user_car_fail";
			case fail9990:
				return "cnter_error";
			case fail9970:
				return "blacklist";
			default:
				return "Unknown";
		}
	}	
	
public static ResCode valueOf(final Integer code) {
		
		final int comp0 = 0;	
		final int fail99 = 99, fail9990=9990;
		final int fail9950 = 9950;
        final int fail9960 = 9960;
        final int fail9970 = 9970;
		
		switch (code) {
		case comp0:
			return ResCode.SUCCESS;
		case fail99:
			return ResCode.NO_DATA;
		case fail9950:
			return ResCode.RCEPT_FAIL;
        case fail9960:
            return ResCode.USE_CAR_FAIL;
        case fail9990:
			return ResCode.FAIL2;
        case fail9970:
			return ResCode.BLACKLIST;
		default:
			return ResCode.FAIL;
		}	
	}
}