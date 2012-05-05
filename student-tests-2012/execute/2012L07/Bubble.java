class Bubble {
	public static void main(String[] ignore) {
		int[] data;
		int i;
		int t;
		boolean s;

		data = new int[20];

		// Populate array
		data[0] = 475025;
		data[1] = 39089371;
		data[2] = 98633482;
		data[3] = 18535809;
		data[4] = 6292128;
		data[5] = 45067761;
		data[6] = 39869153;
		data[7] = 63942395;
		data[8] = 48625988;
		data[9] = 95348451;
		data[10] = 59832692;
		data[11] = 8243805;
		data[12] = 78193854;
		data[13] = 265876;
		data[14] = 1337;
		data[15] = 41455669;
		data[16] = 62806689;
		data[17] = 78061171;
		data[18] = 68091460;
		data[19] = 48108071;

		// The actual sort
		s = true;
		while (s) {
			s = false;
			i = 1;
			while (i < 20) {
				if (data[i - 1] < data[i]) {
					t = data[i - 1];
					data[i - 1] = data[i];
					data[i] = t;
					s = true;
				} else {}
				i = i + 1;
			}
		}

		// Turn to ascending order
		i = 0;
		while (i < 10) {
			t = data[i];
			data[i] = data[19 - i];
			data[19 - i] = t;
			i = i + 1;
		}

		// Print sorted array
		i = 0;
		while (i < 20) {
			System.out.println(data[i]);
			i = i + 1;
		}
	}
}
