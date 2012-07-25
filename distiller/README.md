## Distiller: extracting text content from HTML

Playing around with boilerpipe text extraction.

### Running
To run the project in sbt:
```
sbt
>run http://findarticles.com/p/articles/mi_m0ibx/is_1_10/ai_106059247/pg_2
```

### Packaging
To create a jar file and copy all of the dependencies to the target directory:
```
sbt package copy-dependencies
```

To create a stand-alone jar:
```
sbt proguard
```

Built with Scala 2.9.1 and sbt 0.11.2
