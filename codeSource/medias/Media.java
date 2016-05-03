public abstract class Media{
	protected String mFileName;
	protected int mDuration;
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
}
