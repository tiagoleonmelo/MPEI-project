package projeto;

public class HashFunctionTest {

	public static void main(String[] args) {
		
		// Generate n different strings
		// Hash them accross m slots
		// Check their distribution
		// ????
		// Profit
		
		int n=100;
		int nFuncs=5;
		int[] array=new int[1000];
		int[] hashFunctions = {1, 5, 10, 20, 50, 100};
		
		for(int k=0;k<5;k++) {
			
			nFuncs=hashFunctions[k];
		
			for(int i=0; i < n; i++)
			{
				String randStr=generateString();
				for(int j = 0;j<nFuncs;i++) {
					int hash=myHashFunction(randStr,j);
					hash=hash%1000;
					array[hash]++;
				}
				
			}
			
			// fazer histograma para o k atual aqui
		
		}

	}

	
	public static String generateString() {
        String uuid = Long.toHexString(Double.doubleToLongBits(Math.random()));
        uuid=uuid.substring(2, uuid.length());
        return uuid;
    }
	
	public static int myHashFunction(String key, int index)
	{
		int hash;
		String elemento = key;
		elemento=elemento+(index*Math.pow(2, 32)%12345678);	// trying to get sparser minHashes
		hash = Math.abs(elemento.hashCode());
		return hash;
	}

}
