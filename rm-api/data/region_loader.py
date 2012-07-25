from google.appengine.ext import db
from google.appengine.tools import bulkloader
from models import Region

class RegionLoader(bulkloader.Loader):
	def __init__(self):
		bulkloader.Loader.__init__(self, 'Region', [
			('name', str),
			('code', str),
			('country', str),
		])

loaders = [RegionLoader]