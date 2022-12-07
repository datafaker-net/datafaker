package net.datafaker.providers.base;

import java.util.Random;

public class NumberBases extends AbstractProvider<BaseProviders>{
	protected NumberBases(BaseProviders faker) {
        super(faker);
    }
	private char[] num = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
	
	public String binary(int digits) {
		String result = "";
		Random random = new Random();
		for(int i = 0 ; i < digits ; i++) {
			int r = random.nextInt(1);
			result += num[r];
		}
		return result;
	}
	
	public String octal(int digits) {
		String result = "";
		Random random = new Random();
		for(int i = 0 ; i < digits ; i++) {
			int r = random.nextInt(7);
			result += num[r];
		}
		return result;
	}
	
	public String hexadecimal(int digits) {
		String result = "";
		Random random = new Random();
		for(int i = 0 ; i < digits ; i++) {
			int r = random.nextInt(15);
			result += num[r];
		}
		return "0x"+result;
	}
}
