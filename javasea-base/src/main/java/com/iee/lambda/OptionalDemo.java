package com.iee.lambda;

import com.alibaba.fastjson.JSONObject;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

/**
 * @ClassName: OptionalTest
 * @Description:
 * @author 龙小南
 * @email longxiaonan@163.com
 * @date 2017年12月4日 上午10:01:06
 * @version 1.0
 */
public class OptionalDemo {

	public static void main(String[] args) {

		String  objFromRedis1 = "{\"ALARM\":{\"ALARM_MASK\":\"00000000000000000000000000000000\",\"ALARM_MASK_BRAKE\":\"0\",\"ALARM_MASK_CELL_HIGH_TEMP\":\"0\",\"ALARM_MASK_CELL_UNMATCHED\":\"0\",\"ALARM_MASK_CONTROL_TEMP\":\"0\",\"ALARM_MASK_DCDC_STATE\":\"0\",\"ALARM_MASK_DCDC_TEMP\":\"0\",\"ALARM_MASK_ELECTRIC_MOTOR_TEMP\":\"0\",\"ALARM_MASK_ENERGY_STORAGE_OVER_CHARGE\":\"0\",\"ALARM_MASK_ENERGY_STORAGE_OVER_VOLTAGE\":\"0\",\"ALARM_MASK_ENERGY_STORAGE_UNDER_VOLTAGE\":\"0\",\"ALARM_MASK_HVIL_STATE\":\"0\",\"ALARM_MASK_INSULATION\":\"0\",\"ALARM_MASK_SINGLE_CELL_OVER_VOLTAGE\":\"0\",\"ALARM_MASK_SINGLE_CELL_UNDER_VOLTAGE\":\"0\",\"ALARM_MASK_SINGLE_CELL_UNIFORMITY\":\"0\",\"ALARM_MASK_SOC_HIGH\":\"0\",\"ALARM_MASK_SOC_LOW\":\"0\",\"ALARM_MASK_SOC_SALTUS\":\"0\",\"ALARM_MASK_TEMP_DIFF\":\"0\",\"CELL_ALARM_LIST\":[],\"CELL_ALARM_NUM\":0,\"ELECTRICMOTOR_ALARM_LIST\":[],\"ELECTRICMOTOR_ALARM_NUM\":0,\"MAX_ALARM_LEVEL\":0,\"MOTOR_ALARM_LIST\":[],\"MOTOR_ALARM_NUM\":0,\"OTHER_ALARM_LIST\":[],\"OTHER_ALARM_NUM\":0},\"CELL_TEMP\":{\"CELL_NUM\":1,\"CELL_TEMP_LIST\":[{\"CELL_ID\":1,\"DETECTOR_NUM\":42,\"DETECTOR_VALUE_LIST\":[17,17,19,20,17,17,19,17,17,17,17,19,17,17,17,17,17,17,17,19,17,17,19,17,17,16,17,20,255,255,-40,-40,-40,-40,-40,-40,-40,-40,-40,-40,-40,-40]}]},\"DATATAG\":\"RTINFO\",\"DEVCODE\":\"LB9KB8KE6DENJL221\",\"ELECTRIC_MOTOR\":{\"MOTOR_INFO\":[{\"CTRL_TEMPERATURE\":25,\"MOTOR_FLOW\":65535,\"MOTOR_ID\":1,\"MOTOR_RPM\":1137,\"MOTOR_STATE\":2,\"MOTOR_TEMPERATURE\":46,\"MOTOR_TORQUE\":65535,\"MOTOR_VOLTAGE\":552}],\"MOTOR_NUM\":1},\"EXTREME_VALUE\":{\"MAX_TEMPERATURE_CELL\":1,\"MAX_TEMPERATURE_UNIT\":29,\"MAX_UNIT_TEMPERATURE\":20,\"MAX_UNIT_VOLTAGE\":3.44,\"MAX_VOLTAGE_CELL\":1,\"MAX_VOLTAGE_UNIT\":2,\"MIN_TEMPERATURE_CELL\":7,\"MIN_TEMPERATURE_UNIT\":31,\"MIN_UNIT_TEMPERATURE\":-40,\"MIN_UNIT_VOLTAGE\":0,\"MIN_VOLTAGE_CELL\":1,\"MIN_VOLTAGE_UNIT\":2},\"LOCATION\":{\"ISEW\":0,\"ISNS\":0,\"ISVALID\":0,\"LATITUDE\":31.993128,\"LONGITUDE\":120.88184,\"STATE\":0},\"RECVTIME\":1519687520073,\"SN\":0,\"TIME\":1519687516000,\"VEHICLE\":{\"AP_TRAVEL\":0,\"BRAKE_STATE\":18,\"CHARGE_STATE\":3,\"DC_STATE\":1,\"FLOW\":-47.6,\"GEAR_IS_BRAKE\":0,\"GEAR_IS_DRIVE\":0,\"GEAR_VALUE\":14,\"MILEAGE\":0,\"RESISTOR\":16157,\"RUN_MODE\":1,\"SOC\":82,\"SPEED\":44,\"VOLTAGE\":552.9,\"V_STATE\":1}}";
		Integer orElse = Optional.ofNullable(objFromRedis1).map(a -> JSONObject.parseObject(a)).map(b -> b.getJSONObject("ELECTRIC_MOTOR"))
//				.filter(c -> c.getString("MOTOR_INFO") != null)
				.filter(c -> !Objects.equals(null, c.getString("MOTOR_INFO")))
				.map(d -> d.getJSONArray("MOTOR_INFO"))
				.map(e->e.getJSONObject(0))
//				.filter(f -> f.getInteger("MOTOR1_STATE") == 2)
				.filter(f -> Objects.equals(f.getString("MOTOR_STATE"),"2"))
				.map(g -> g.getInteger("MOTOR_STATE"))
				.orElse(250);
		System.out.println(orElse);


//		String objFromRedis2 = "{\"releaseData\":1512316800000,\"assetCode\":\"60100000117290991\",\"code\":\"60100000117290991\",\"delFlag\":0,\"deviceId\":\"eaccbafc9eaa4247bbf2e42e37cf9b05\",\"orgId\":\"fda47aacce584fd49360b1b2cfa6c0e4\",\"versionId\":\"\",\"createTime\":1512369526000,\"imei\":\"898602B5071700073203\",\"model\":\"N100\",\"simCode\":\"1064856393203\",\"bindStatus\":1,\"lastUpdateTime\":1512369623000}";
//		boolean orElse2 = Optional.ofNullable(objFromRedis2).map(n -> JSONObject.parseObject(n))
//				.filter(m -> Objects.equals("60100000",m.getString("assetCode"))).isPresent();
//		System.out.println(orElse2);


		// 创建Optional实例，也可以通过方法返回值得到。
		Optional<String> name = Optional.of("Sanaulla");

		// 创建没有值的Optional实例，例如值为'null'
		Optional empty = Optional.ofNullable(null);

		// isPresent方法用来检查Optional实例是否有值。
		if (name.isPresent()) {
			// 调用get()返回Optional值。
			System.out.println(name.get());
		}

		Optional<Object> o = Optional.ofNullable(null);
		o.ifPresent(a ->{});

		try {
			// 在Optional实例上调用get()抛出NoSuchElementException。
			System.out.println(empty.get());
		} catch (NoSuchElementException ex) {
			System.out.println(ex.getMessage());
		}

		// ifPresent方法接受lambda表达式参数。
		// 如果Optional值不为空，lambda表达式会处理并在其上执行操作。
		name.ifPresent((value) -> {
			System.out.println("The length of the value is: " + value.length());
		});

		// 如果有值orElse方法会返回Optional实例，否则返回传入的错误信息。
		System.out.println(empty.orElse("There is no value present!"));
		System.out.println(name.orElse("There is some value!"));

		// orElseGet与orElse类似，区别在于传入的默认值。
		// orElseGet接受lambda表达式生成默认值。
		System.out.println(empty.orElseGet(() -> "Default Value"));
		System.out.println(name.orElseGet(() -> "Default Value"));

		try {
			// orElseThrow与orElse方法类似，区别在于返回值。
			// orElseThrow抛出由传入的lambda表达式/方法生成异常。
			empty.orElseThrow(ValueAbsentException::new);
		} catch (Throwable ex) {
			System.out.println(ex.getMessage());
		}

		// map方法通过传入的lambda表达式修改Optonal实例默认值。
		// lambda表达式返回值会包装为Optional实例。
		Optional<String> upperName = name.map((value) -> value.toUpperCase());
		System.out.println(upperName.orElse("No value found"));

		// flatMap与map（Funtion）非常相似，区别在于lambda表达式的返回值。
		// map方法的lambda表达式返回值可以是任何类型，但是返回值会包装成Optional实例。
		// 但是flatMap方法的lambda返回值总是Optional类型。
		upperName = name.flatMap((value) -> Optional.of(value.toUpperCase()));
		System.out.println(upperName.orElse("No value found"));

		// filter方法检查Optiona值是否满足给定条件。
		// 如果满足返回Optional实例值，否则返回空Optional。
		Optional<String> longName = name.filter((value) -> value.length() > 6);
		System.out.println(longName.orElse("The name is less than 6 characters"));

		// 另一个示例，Optional值不满足给定条件。
		Optional<String> anotherName = Optional.of("Sana");
		Optional<String> shortName = anotherName.filter((value) -> value.length() > 6);
		System.out.println(shortName.orElse("The name is less than 6 characters"));

	}

}

class ValueAbsentException extends Throwable {

	public ValueAbsentException() {
		super();
	}

	public ValueAbsentException(String msg) {
		super(msg);
	}

	@Override
	public String getMessage() {
		return "No value present in the Optional instance";
	}
}
