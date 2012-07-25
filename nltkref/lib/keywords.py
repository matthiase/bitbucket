import re
from nltk.tokenize import sent_tokenize, word_tokenize
from nltk.tag import pos_tag
from nltk.chunk import RegexpParser
from lib.collocations import find_collocations


def find_keywords(text):
  """
    Extracts keywords from text.

    Args:
      text: A text fragment.

    Returns:
      A list containing the extracted keywords.
  """
  grammar = r'''
    KEYWORD: {<NNP><NNP>+}
        {<NN.*><NN.*>+}
        {<JJ>+<NN>+}
  '''
  parser = RegexpParser(grammar)
  sentences = [ ]
  words = [ ]
  keywords = [ ]
  for sentence in sent_tokenize(text):
    tokens = word_tokenize(sentence)
    if not tokens: continue
    sentences.append(tokens)
    words += tokens

  collocations = find_collocations(words)

  for sentence in sentences:
    tree = parser.parse(pos_tag(sentence))
    for node in _select_nodes(tree, ['KEYWORD']):
      word = ' '.join(map(lambda p: p[0], node))
      if word in collocations:
        keywords.append(word)

  keywords = sorted(keywords, key=lambda k: len(k.split()), reverse=True) 
  instances = { }
  for k in keywords:
    key = k
    for existing in instances.keys():
      if re.match(k, existing):
        key = existing
        break
    instances[key] = instances.get(key, 0) + 1 
  results = instances.items()
  results.sort(key=lambda item: int(item[1]), reverse=True)
  return map(lambda item: item[0], results)


def _select_nodes(tree, tags):
  return [t.leaves() for t in tree.subtrees(lambda s: s.node in tags)]
