package com.iee.geogMap;
   /**
    * @ClassName: mapConst
	* @Description:
    * @author 龙小南
    * @email longxiaonan@163.com
    * @date 2017年11月16日 下午12:18:02
    * @version 1.0
    */
enum MapConst {
	key("03d371c5c9927a9522fc046a5ac08ea9");

	private String value;
	private MapConst(String value){
		this.value = value;
	}
	@Override
	public String toString() {
		return value;
	}
}
