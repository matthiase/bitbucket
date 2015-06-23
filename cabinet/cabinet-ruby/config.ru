require 'bundler/setup'
require 'sinatra'
require 'haml'
require 'yaml'
require 'aws-sdk'
require 'json'
require './app'

set :run, false
set :raise_errors, true
disable :sessions, :logging

set :environment, 'production'

amazon = YAML::load_file('amazon.yml')
AWS.config(amazon['credentials'])

set :bucket, amazon['bucket']

run Sinatra::Application
