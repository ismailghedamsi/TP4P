import java.io.*;
import java.util.*;
public  class MediaVideo extends Media{
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
        int pFrameRate=0;
        int pHeight=0;
        int pWidth=0;
		int mediaType=-1;
		String currentLine=null;
	    mediaType = Integer.valueOf(pScan.nextLine());
		System.out.println("mediaType"+mediaType);
		
		do{
			currentLine = pScan.nextLine();
			System.out.println(currentLine);
			
			if(currentLine.equals("FRAME_RATE")){
				mFrameRate = Integer.valueOf(pScan.nextLine());
			}
			System.out.println(currentLine.equals("HEIGHT"));
			if(currentLine.equals("HEIGHT")){
				pHeight = Integer.valueOf(pScan.nextLine());
				System.out.println("pHeight"+pHeight);
			}
			
			if(currentLine.equals("WIDTH")){
				pWidth = Integer.valueOf(pScan.nextLine());
			}
		}while(!currentLine.isEmpty());
	    mFrameRate = pFrameRate;
	    
		mHeight = pHeight;
		mWidth = pWidth;
        
	}
	
	public String[] getFullArrayInfo(){
		return null;
	}
	
	public String toString(){
		return "Framerate "+mFrameRate+"\nWidth "+mWidth+"\nHeight"+mHeight;
	}

	
	  public static void main(String[] args) throws IOException{
		Scanner pScan=null;
			 pScan = new Scanner(new File("libLocal.txt"));
			 if(pScan == null){
				 throw new IOException("Fichier de la library local introuvable");
			 }
			 MediaVideo mv = new MediaVideo(pScan);	
			 System.out.println(mv);

	}
	
}
