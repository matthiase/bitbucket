# app.rb
set :haml, :format => :html5

get "/status" do
  {:status => "ok"}.to_json
end
 
get "/" do
  haml :index
end

post "/" do
  filename = params['file'][:filename]
  tempfile = params['file'][:tempfile]

  s3 = AWS::S3.new
  bucket = s3.buckets[settings.bucket] || s3.buckets.create(settings.bucket)

  obj = bucket.objects[filename]
  obj.write(tempfile.read)

  # Provide the user with a pre-signed url
  "<h2>Upload Successful</h2><a target=\"_blank\" href=\"#{obj.url_for(:read)}\">#{obj.url_for(:read)}</a>"
end
