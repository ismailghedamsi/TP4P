package medias;
import java.io.*;
import java.util.*;
public  class MediaVideo extends Media implements MediaConstant{
	private int mFrameRate;
	private int mWidth;
	private int mHeight;
    private final int NBMETADATA=6;

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

			   String currantLine = "_" ;
			   while(!currantLine.isEmpty()){
				   currantLine = pScan.nextLine();
				   if(currantLine.trim().equals("FILE_NAME")){
					   mFileName = pScan.nextLine();
				   }else if(currantLine.trim().equals("DURATION")){
					    mDuration = Integer.valueOf(pScan.nextLine());
				   }else if(currantLine.trim().equals("YEAR")){
					   mYear = Integer.valueOf(pScan.nextLine());
				   }else if(currantLine.trim().equals("TITLE")){
					   mTitle = pScan.nextLine();
				   }else if(currantLine.trim().equals("FRAME_RATE")){
					   mFrameRate = Integer.valueOf(pScan.nextLine());
				   }else if(currantLine.trim().equals("HEIGHT")){
					   mHeight = Integer.valueOf(pScan.nextLine());
				   }else if(currantLine.trim().equals("WIDTH")){
					   mWidth = Integer.valueOf(pScan.nextLine());
				   }
				   
			   }
	       }
	    
	public int getFrameRate(){
		return mFrameRate;
	}  
	
	public int getWidth(){
		return mWidth;
	}
	
	public int getHeight(){
		return mHeight;
	}
	
	public String[] getFullArrayInfo(){
		String[] metaDataContainer = new String[NBMETADATA];
		metaDataContainer[TITLE]=mTitle;
		metaDataContainer[YEAR]=mYear+"";
		metaDataContainer[DURATION]=mDuration+"";
		metaDataContainer[FRAME_RATE]=mFrameRate+"";
		metaDataContainer[DIMENSION]=mWidth+"X"+mHeight;
		metaDataContainer[FILE_NAME]=mFileName;
		return metaDataContainer;
	}
	
	public String toString(){
		return super.toString()+"\nFramerate\n"+mFrameRate+"\nWidth\n"+mWidth+"\nHeight\n"+mHeight;
	}
	
	  public static void main(String[] args) throws IOException{
		Scanner pScan=null;
			 pScan = new Scanner(new File("../libLocal.txt"));
			 if(pScan == null){
				 throw new IOException("Fichier de la library local introuvable");
			 }
			 Media mv = new MediaVideo("medias/vid1.mp4",3,2000,"am i a psycho",300,600,600);	
			 Media mv2 = new MediaVideo(pScan);
			 System.out.println(mv2.toString());

	}
	
}
