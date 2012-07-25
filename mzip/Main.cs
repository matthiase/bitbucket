using System;


namespace mzip
{
	/// <summary>
	/// Summary description for Main.
	/// </summary>
	class _Main
	{

        static Mzip m_app = new Mzip();

		/// <summary>
		/// The main entry point for the application.
		/// </summary>
		[STAThread]
		static void Main(string[] args)
		{
            GetArgs(ref m_app, args);
            string s = "\n";
            s += (m_app.Mode == Mzip.CompressionMode.compress) ? "compressing" : "decompressing";
            s += " " + m_app.InputFile;
            Console.WriteLine(s);

            if (m_app.Mode == Mzip.CompressionMode.compress)
            {
                m_app.compress();
            }
            else if (m_app.Mode == Mzip.CompressionMode.decompress)
            {

            }

    }



        /// <summary>
        /// 
        /// </summary>
        /// <param name="app"></param>
        /// <param name="args"></param>
        private static void GetArgs(ref Mzip app, string[] args)
        {
            for (int i = 0; i < args.Length; i++)
            {
                switch (args[i])
                {                    
                    case "/c":
                        app.Mode = Mzip.CompressionMode.compress;
                        break;

                    case "/d":
                        app.Mode = Mzip.CompressionMode.decompress;
                        break;

                    case "/v":
                        Console.WriteLine(Mzip.Name + " v" + Mzip.Version);
                        break;

                    case "/h":
                        Console.WriteLine(Mzip.Usage);
                        break;

                    default:
                        if (app.InputFile.Length == 0)
                        {
                            app.InputFile = args[i];
                        }
                        break;
                }
            }
        }


	}
}
