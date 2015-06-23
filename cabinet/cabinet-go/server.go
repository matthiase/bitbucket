package main

import (
  //_ "github.com/bmizerany/pq"
  //"database/sql"
  //"runtime"
  "net/http"
  "io"
  "fmt"
  "time"
  "strconv"
  "encoding/json"
)

type Message struct {
 Status string 
}

func StatusHandler(resp http.ResponseWriter, req *http.Request) {
  m := Message{"ok"}
  b, err := json.Marshal(m)
  if (err != nil) {
    fmt.Println(err)
    return 
  }
  body := string(b)
  //resp.Header().Add("Connection", "keep-alive")
  resp.Header().Add("Content-Type", "application/json")
  resp.Header().Add("Content-Length", strconv.Itoa(len(body)))
  io.WriteString(resp, body)
}

func HelloHandler(resp http.ResponseWriter, req *http.Request) {
  /*
  db, err := sql.Open("postgres", "user=meder dbname=foobar sslmode=disable") 

  if (err != nil) {
    fmt.Println(err)
    return 
  }
  defer db.Close()

  stmt, err := db.Prepare("SELECT firstname, lastname FROM members")
  if (err != nil) {
    fmt.Println(err)
    return 
  }
  defer stmt.Close()

  rows, err := stmt.Query()
  if (err != nil) {
    fmt.Println(err)
    return 
  }
  defer rows.Close()

  for rows.Next() {
    var firstname string
    var lastname string
    rows.Scan(&firstname, &lastname)
    resp.Write([]byte("Hello, " + firstname + " " + lastname + "\n"))
  }
  */
  body := "Hello, Matthias Eder\n"
  resp.Header().Add("Connection", "keep-alive")
  resp.Header().Add("Content-Type", "text/plain")
  resp.Header().Add("Content-Length", strconv.Itoa(len(body)))
  io.WriteString(resp, body)
}

func main() {
  //runtime.GOMAXPROCS(runtime.NumCPU())
  fmt.Printf("Starting server...")
  http.HandleFunc("/status", StatusHandler)
  http.HandleFunc("/hello", HelloHandler)
  server := &http.Server {
    Addr:   ":8080",
    Handler:  http.DefaultServeMux,
    ReadTimeout: 3 * time.Second,
    WriteTimeout: 3 * time.Second,
  }
  server.ListenAndServe()
}

