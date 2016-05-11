package medias;
import java.io.*;
import java.util.*;
public  class MediaVideo extends Media implements MediaConstant{
	private int mFrameRate;
	private int mWidth;
	private int mHeight;
	 private int MP3=1;
	  private int MP4=0;

	public MediaVideo(String pFileName,int pDuration,int pYear,String pTitle,int pFrameRate,int pWidth,int pHeight){
		super(pFileName,pDuration,pYear,pTitle);
		mFrameRate = pFrameRate;
		mWidth = pWidth;
		mHeight = pHeight;
	}
	
	public MediaVideo(MediaVideo pMedia){
		  super(pMedia);
		 this.mFrameRate = pMedia.mFrameRate;
		 this.mWidth = pMedia.mWidth;
		 this.mHeight = pMedia.mHeight;
	  }
	  
	   public MediaVideo(Scanner pScan){
		       //int typeMedia;
		       //typeMedia = Integer.valueOf(pScan.nextLine());
			   pScan.nextLine();
			   mFileName = pScan.nextLine();
			   pScan.nextLine();
			   mDuration = Integer.valueOf(pScan.nextLine());
			   pScan.nextLine();
			   mYear = Integer.valueOf(pScan.nextLine());
			   pScan.nextLine();
			   mTitle = pScan.nextLine();
			   pScan.nextLine();
			   mFrameRate = Integer.valueOf(pScan.nextLine());
			   pScan.nextLine();
			   mWidth = Integer.valueOf(pScan.nextLine());
			   pScan.nextLine();
			   mHeight = Integer.valueOf(pScan.nextLine());
	       }
	    
	
	public String[] getFullArrayInfo(){
		return null;
	}
	
	public String toString(){
		return super.toString()+"\nFramerate "+mFrameRate+"\nWidth "+mWidth+"\nHeight"+mHeight;
	}

	
	  public static void main(String[] args) throws IOException{
		Scanner pScan=null;
			 pScan = new Scanner(new File("../libLocal.txt"));
			 if(pScan == null){
				 throw new IOException("Fichier de la library local introuvable");
			 }
			 Media mv = new MediaVideo("medias/vid1.mp4",3,2000,"am i a psycho",300,600,600);	
			 Media mv2 = new MediaVideo(pScan);
			 System.out.println(mv2);

	}
	
}
