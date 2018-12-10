package projeto;

public class CBFTest {

	public static void main(String[] args) {
		
		 /* 
		 * 
		 * Para testar o nosso Counting Bloom Filter, usaremos o mesmo raciciocinio seguido
		 * nos guioes praticos: inserimos n strings aleatorias no filtro e, posteriormente,
		 * procuramos m strings aleatorias nesse mesmo filtro. A probabilidade de serem geradas 2 strins iguais
		 * e desprezivel, logo o acontecimento de uma string gerada ser de facto membro do cbf e reduzido
		 * ao impossivel. Sempre que for detetada uma string no cbf consideramos como falso positivo!
		 *
		 */
		
		CountingBloomFilter cbf;
		int[] nFuncs= {1,10,20,50,100,500};
		String texto;
		int fp;
		int n=80000,m=10000;
		double percentagem, pteorica;
		
		for(int nFunc=0;nFunc<6;nFunc++) {
			
			fp=0;
			
			cbf = new CountingBloomFilter(n,nFuncs[nFunc]);
		
			for(int i = 0; i < m; i++) {
				texto=generateString();
				cbf.insert(generateString());
			}
			
			for(int i = 0; i < 100000; i++) {
				texto=generateString();
				if(cbf.isMember(texto)) {
					fp++;
				}
			}
			
			
			percentagem=(double)fp/(double)100000;
			double e=Math.exp((double)-nFuncs[nFunc]*m/n);
			pteorica=Math.pow(1-e,nFuncs[nFunc]);
			
			System.out.println("Percentagem prática de falsos positivos para " + nFuncs[nFunc] + " hashfunctions: " + percentagem*100 + "%");
			System.out.println("Percentagem teórica de falsos positivos para " + nFuncs[nFunc] + " hashfunctions: " + pteorica*100 + "%");
			System.out.println("----------------------------------------------------------------------------------------------");

		}
				

	}
	
	public static String generateString() {
        String uuid = Long.toHexString(Double.doubleToLongBits(Math.random()));
        uuid=uuid.substring(2, uuid.length());
        return uuid;
    }

}
