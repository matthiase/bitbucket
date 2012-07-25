using System;
using System.IO;
using System.Text;

namespace mzip
{
	/// <summary>
	/// Summary description for Mzip.
	/// </summary>
	public class Mzip
	{        
        public enum CompressionMode
        {
            compress,
            decompress
        }


        private CompressionMode m_mode = CompressionMode.compress;
        private string m_input = "";

        private const string FILE_EXT = ".mzip";  
        private const int BUFFER_SIZE = 4096;

        private const string APP_NAME = "MZIP Compression Utility";
        private const string APP_VERSION = "1.0.1";
        private const string AUTHOR = "Matthias Eder";


        public Mzip()
        {
        
        }


        public CompressionMode Mode
        {
            get { return m_mode; }
            set { m_mode = value; }
        }


        public string InputFile
        {
            get { return m_input; }
            set { m_input = value; }
        }



        public void compress()
        {
            if (InputFile.Length > 0)
            {
                try
                {
                    string basename = InputFile;
                    int index = basename.LastIndexOf(".");

                    if (index != -1)
                    {
                        basename = basename.Remove(index, basename.Length - index);            
                    }

                    Console.WriteLine(basename);
                    
                    //FileStream fin = null;
                    TextReader fin = null;
                    TextWriter fout = null;

                    try
                    {                               
                        fin = new StreamReader(new FileStream(InputFile, FileMode.Open, 
                            FileAccess.Read), Encoding.Default);
                        
                        fout = new StreamWriter(new FileStream(basename + FILE_EXT, FileMode.Create,
                            FileAccess.ReadWrite), Encoding.Default);

                        int size = 0;
                        char[] buffer = new char[BUFFER_SIZE];

                        while ((size = fin.Read(buffer, 0, BUFFER_SIZE)) > 0) 
                        {
                            fout.Write(LZSS.deflate(buffer, size));
                        }
                    }
                    catch (Exception ex)
                    {
                        throw new InvalidOperationException("Unknown error occurred.", ex);
                    }
                    finally
                    {   
                        if (fin != null)
                        {
                            fin.Close();
                        }

                        if (fout != null)
                        {
                            fout.Flush();
                            fout.Close();
                        }
                    }

                }
                catch (FileNotFoundException)
                {
                    throw new InvalidOperationException("File " + InputFile + " does not exist."); 
                }
                catch (IOException ex)
                {
                    throw new InvalidOperationException("Unknown IO Exception occurred", ex);
                }
                catch (Exception ex)
                {
                    throw new InvalidOperationException("Unknown Exception occurred", ex);
                }
            }
            else
            {
                throw new InvalidOperationException("Input file must be specified");
            }
    
        }
        




        public static string Name
        {
            get { return APP_NAME; }
        }
    

        public static string Version
        {
            get { return APP_VERSION; }
        }


        public static string Author
        {
            get { return AUTHOR; }
        }


        public static string Usage
        {
            get
            {
                string text = Name + " v" + Version + "\n\n";
                text += "Usage:\n";
                text += "\tmzip [options] infile [outfile]";
                return text;
            }
        }
	}
}
