package demos;

public class UseStringForEnumDemo {
	
	public enum EBVariablesIdEnum {
		MessageSender("10001"),
		NotificationStatus("10002"),
		CurrentTime("10003"),
		TodaysDate("10004");
		
		private String id;
		
		EBVariablesIdEnum(String id) {
			this.id = id;
		}
		
		public String getId() {
			return this.id;
		}
	}
	
	public static void parseStringEnum(String enum_field) {
		String field = enum_field.replaceAll("EBVariablesIdEnum\\.", "");
		for (EBVariablesIdEnum enumValue : EBVariablesIdEnum.values()) {
			if (enumValue.toString().equals(field)) {
				System.out.println(enumValue.getId());
			}
		}
	}
	
	public static void main(String[] args) {
		parseStringEnum("EBVariablesIdEnum.TodaysDate");
	}

}
