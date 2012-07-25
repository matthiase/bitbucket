import re

__replacements = [
    (re.compile(r"\s+", re.MULTILINE), ' '),
    (re.compile(r"\xe2\x80\x99"), "'"),
    (re.compile(r"\xe2\x80\x9c|\xe2\x80\x9d"), "\""),
    (re.compile(r"\xe2\x80\x93"), "-")
]

def clean(value):
  """
  Removes undesirable characters from the text.

  Args:
    value: Raw text that may or may not include undesirable character sequences.

  Returns:
    A string containing the original text, less any characters that matching one
    or more replacement rules.
  """
  text = value
  for regexp,replacement in __replacements:
    text = regexp.sub(replacement, text)
  return text

