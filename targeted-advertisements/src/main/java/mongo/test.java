package mongo;


public class test {
	
	
	public test() throws Exception {
	}
	
	
	@SuppressWarnings("null")
	public static void main(String args[])
	{
		
		String string = null;
		//char[] charArr = string.toCharArray();
		
		if(string!=null)
		{
		System.out.println(string.length());
		//System.out.println(charArr.length);
		System.out.println(string.charAt(5));
		if(string.charAt(5)=='-')
		{
			System.out.println("in");
		}
		}
		else{
			System.out.println("else");
		}
		
		/*for(int i=0;i<charArr.length;i++)
		{
			System.out.println(charArr[i]);
			String temp = String.valueOf(charArr[i]);
			for(int j=i+1;j<charArr.length;j++)
			{
				temp += charArr[j];
				System.out.println(temp);
			}
		}*/
	}
}
