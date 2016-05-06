import java.io.*;
import java.util.*;
public abstract class Media{
	protected String mFileName;
	protected int mDuration; //en seconde
	protected int mYear;
	protected String mTitle;
	
	
	public Media(String pFileName,int pDuration,int pYear,String pTitle){
		mFileName = pFileName;
		mDuration = pDuration;
		mYear = pYear;
		mTitle = pTitle;
	}
	
	public Media(Media pMedia){
		mFileName = pMedia.mFileName;
		mDuration = pMedia.mDuration;
		mYear = pMedia.mYear;
		mTitle = pMedia.mTitle;
	} 
	
    public static String[] getFullArrayInfo(){
		String[] mediaTas;
		int mediaType=-1;
		try{
			Scanner sc = new Scanner(new File("libLocal.txt"));
			while(sc.hasNext()){
				mediaType = Integer.valueOf(sc.next());
			}
	    }catch(IOException ioe){
			ioe.getMessage();
		}
		System.out.println("Fichier "+mediaType);
		return null;
	}
	
	public void setFileName(String pFileName){
		mFileName = pFileName;
	}
	
	public String getFileName(){
		return mFileName;
	}
	
	public int getDuration() {
		return mDuration;
	}
	
	public String getTitle(){
		return mTitle;
	}
	
	public int getYear(){
		return mYear;
	}
	
	/*Main de debug
	 * */
	public static void main(String[] args){
		Media.getFullArrayInfo();
	}
	

}
