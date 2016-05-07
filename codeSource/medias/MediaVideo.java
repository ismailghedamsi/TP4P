public  class MediaVideo extends Media{
	private String mFrameRate;
	private int mWidth;
	private int mHeight;
	
	public MediaVideo(String pFileName,int pDuration,int pYear,String pTitle,String pFrameRate,int pWidth,int pHeight){
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
	
}
