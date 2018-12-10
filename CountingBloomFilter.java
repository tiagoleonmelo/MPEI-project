package projeto;


public class CountingBloomFilter {
	
	// seguir raciocinio das praticas
	// ou seja, so vou ter um array, cujo tamanho e passado como argumento no construtor
	// e depois vou incrementar e tal, dependendo da elemento passada e da hash function que tenho?
	// string2hash i guess.
	
	private int size, nFuncs;
	private int[] array;
	
	public CountingBloomFilter(int size, int nFuncs) {
		this.size=size;
		this.array= new int[size];
		this.nFuncs=nFuncs;
		
	}
	
	public void insert(String elemento) {
		String key = elemento;
		for(int i = 0; i < nFuncs; i++) {
			key=key+i;
			int hash = Math.abs(key.hashCode());
			int index = Math.abs(hash%size);
			//System.out.println("elemento: " + elemento + "; hash: "+hash);
			array[index]++;
		}
	}
	
	public int delete(String elemento) {
		if(this.isMember(elemento)) {
			String key = elemento;
			for(int i = 0; i < nFuncs; i++) {
				key=key+i;
				int hash = Math.abs(key.hashCode());
				int index = Math.abs(hash%size);
				array[index]--;
			}
			return 1;
		}else {
			return -1;
		}
	}
	
	public boolean isMember(String elemento) {
		String key = elemento;
		for(int i = 0; i < nFuncs; i++) {
			key=key+i;
			int hash = Math.abs(key.hashCode());
			int index = Math.abs(hash%size);
			if(array[index]==0) {
				return false;
			}
		}
		return true;
	}
	
	public int count(String elemento) {
		int returnThis=0;
		String key = elemento;
		for(int i = 0; i < nFuncs; i++) {
			key=key+i;
			int hash = Math.abs(key.hashCode());
			int index = Math.abs(hash%size);
			if(array[index]<returnThis || i==0) {
				returnThis=array[index];
			}
		}
		return returnThis;
		
	}
	
	public int newHashCode(String key) {
		int hash=1;
		for(int i=0; i < key.length();i++) {
			hash=hash*(key.length()-i)+(int) key.charAt(i);
		}
		return Math.abs(hash);
	}
}
