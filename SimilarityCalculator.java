package projeto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class SimilarityCalculator {
	
	// min hash e o minimo da funçao hash
	// aplicar hash functions a cada uma das palavras dos documentos e vou buscar a min hash gerada por cada um
	// se usar n hash functions vou acabar com n min hashes;
	// um documento e caracterizado por essas n min hashes -> conjunto de min hashes aka assinatura;
	// aplicar um jaccard meio chines nesses dois conjuntos de min hashes;
	// se forem iguais interseçao++;
	// a similaridade vai ser dada por interseçao/n;
	
	int nFuncs;
	int nFiles;
	ArrayList<String>[] words;	// array de arraylists onde cada posiçao ira conter um array com as palavras do ficheiro da posiçao i do array de ficheiros passado como argumento
	
	@SuppressWarnings("unchecked")
	public SimilarityCalculator(File[] files, int nFuncs) throws FileNotFoundException {
		this.nFuncs=nFuncs;
		this.nFiles=files.length;
		Scanner[] scanArray = new Scanner[nFiles];
		this.words=new ArrayList[nFiles];
		
		for(int i=0; i < files.length; i++) {
			scanArray[i] = new Scanner(files[i]);
			words[i]=new ArrayList<String>();
			while(scanArray[i].hasNext()) {
				words[i].add(scanArray[i].next());
			}
			scanArray[i].close();
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public SimilarityCalculator(String[][] wordArrays, int nFuncs) throws FileNotFoundException {
		this.nFuncs=nFuncs;
		this.nFiles=wordArrays.length;
		this.words=new ArrayList[nFiles];
		
		for(int i=0; i < nFiles; i++) {
			words[i]=new ArrayList<>();
			for(int j=0; j < wordArrays[i].length; j++) {
				if(wordArrays[i][j]!=null) {
					words[i].add(wordArrays[i][j]);
				}
			}
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public SimilarityCalculator(ArrayList<ArrayList<String>> arrList, int nFuncs) {
		this.nFuncs = nFuncs;
		this.nFiles = arrList.size();
		this.words = new ArrayList[nFiles];

		for (int i = 0; i < nFiles; i++) {
			words[i] = arrList.get(i);
		}
	}
	
	public SimilarityCalculator(ArrayList<String>[] arr, int nFuncs) { //novo construtor
		this.nFuncs = nFuncs;
		this.nFiles = arr.length;
		this.words=arr;
	}
	
	public void printWords() {		// prints every line of every file
		int line;
		int i;
		for(int file=0; file < this.nFiles; file++) {
			System.out.println("FICHEIRO " + (file+1));
			line=1;
			for(i = 0;i<words[file].size();i++) {
				System.out.println("Linha " + line + "->" + words[file].get(i));
				line++;
			}
		}
	}
			
	public int[][] getSignatureMatrix() {
		int hash, minHash;
		int assinatura[][]=new int[nFuncs][nFiles];
		for(int ficheiro = 0; ficheiro < this.nFiles; ficheiro++) {
			for(int i = 0;i<nFuncs;i++) {
				minHash=1000000000;	//resetting the minhash
				for(int j = 0; j < words[ficheiro].size(); j++) {
					String elemento = words[ficheiro].get(j);
					String key = elemento;
					key=key+(i*Math.pow(2, 32)%12345);	// trying to get sparser minHashes
					hash = Math.abs(key.hashCode());
					if(hash < minHash) {
						minHash=hash;
					}else if(hash > 1000000000) {
						hash=Math.floorDiv(hash,2);
						if(hash < minHash) {
							minHash=hash;
						}
					}
				}
				assinatura[i][ficheiro]=minHash;
			}
		}
		return assinatura;
	}
	
	
	public double[][] getSimilarity() {	
		
		double[][] similarity = new double[nFiles][nFiles];
		//int[] assinatura1 = new int[nFuncs];
		//int[] assinatura2 = new int[nFuncs];
		
		/*for(int mds=0;mds < nFiles;mds++) {
			int iguais = 0;

			for(int linha=0;linha < nFuncs;linha++) {
				assinatura1[linha]=this.getSignatureMatrix()[linha][mds];
			}
			
			for(int mds2=mds+1; mds2 < nFiles;mds2++) {
				
				for(int linha=0;linha < nFuncs;linha++) {
					assinatura2[linha]=this.getSignatureMatrix()[linha][mds2];
				}
			
				for(int i = 0; i < nFuncs; i++) {
					for(int j = 0; j < nFuncs; j++) {
						if(assinatura1[i]==assinatura2[j]) {
							iguais++;
							break;
						}
					}
				}
								
				similarity[mds][mds2]=(double)iguais/(double)nFuncs;
				
			}
		
		}*/
		
		int[][] sign= this.getSignatureMatrix();
		
		for(int file1=0;file1 < nFiles-1;file1++) {
			
			for(int file2=file1+1;file2<nFiles;file2++) {
				
				int iguais=0;
		
				for(int i=0;i < this.nFuncs;i++) {
					
					for(int j=0;j < this.nFuncs;j++) {
						
						if(sign[i][file1]==sign[j][file2]) {
							iguais++;
						}
					}
				}
				
				similarity[file1][file2]=(double)iguais/(double)nFuncs;
			}
		}
		
		
		return similarity;
	}
	
	public int newHashCode(String key) {
		int hash=1;
		for(int i=0; i < key.length();i++) {
			hash*=(int) key.charAt(i);
		}
		return hash;
	}
	
	public static ArrayList<String> makeShingles(String filepath, int k) throws IOException {
		File file = new File(filepath);
		
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		String st;
		ArrayList<String> conjShingles = new ArrayList<String>();
		
		while ((st = br.readLine()) != null) {
			for (int i = 0; i < st.length() - k + 1; i++) {
				conjShingles.add(st.substring(i, i + k));
			}
		}

		br.close();

		return conjShingles;
	}
	
	

}
