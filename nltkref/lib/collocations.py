from nltk.corpus import stopwords
from nltk.collocations import BigramCollocationFinder, TrigramCollocationFinder
from nltk.metrics import BigramAssocMeasures, TrigramAssocMeasures

_stopset = list(stopwords.words('english')) + ['many', 'would', 'take', 'already', 'said', 'also', 'asked']

def find_collocations(words):
  """
  Find trigram and bigram collocations in text.
  
  Args:
    words - an array of tokenized words.

  Returns:
    A list of collocations, sorted by score.  
  """
  ignore_words = lambda w: len(w) < 3 or w.lower() in _stopset
  trigram_finder = TrigramCollocationFinder.from_words(words)
  trigram_finder.apply_word_filter(ignore_words)
  collocations = trigram_finder.nbest(TrigramAssocMeasures.raw_freq, 10)
  bigram_finder = BigramCollocationFinder.from_words(words)
  bigram_finder.apply_word_filter(ignore_words)
  collocations += bigram_finder.nbest(BigramAssocMeasures.raw_freq, 25)
  return map(lambda w: ' '.join(w), collocations)
