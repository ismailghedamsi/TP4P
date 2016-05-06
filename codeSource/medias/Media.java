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
	
    public String[] getFullArrayInfo(){
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
	

}
