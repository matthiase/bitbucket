using System;
using System.Text;

namespace mzip
{
	/// <summary>
	/// Summary description for LZSS.
	/// </summary>
	public class LZSS
	{

		public LZSS()
		{

		}



        public static char[] deflate(char[] src, int size)
        {
            char[] buffer = new char[size];
            for (int i = 0; i < size; i++)
            {
                buffer[i] = Char.ToUpper(src[i]);
            }
            
            return buffer;
                        
        }





	}
}
