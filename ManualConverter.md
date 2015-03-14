# Manual Converter #

## Key Words ##
Manual, LaTeX, wiki, documentation, converting.

## Author ##
Joe Thomas

## Introduction ##
These wiki pages serve as a rough draft of the manual for our code. One of their chief advantages is that they can be updated rapidly, and are accessible to everyone on the project. Once a piece of documentation has "matured" we would like to incorporate it into our more polished LaTeX based manual. Our manual-converter code provides a convenient way to automate this process.

## Description ##
Many other programmers have solved the problem of converting between LaTeX source and some other text-formatting scheme. So, the main problem this package solves is: "How can I compose these existing solutions into a program that suits our needs?" We accomplish this with two converter programs, `wikify.py` and `html2latex`.

Here is a brief version of what happens to convert `foo.wiki` to `foo.tex`:
  1. The `wikify.py` script converts `foo.wiki` to `foo.html`.
  1. Several `sed` commands remove from `foo.html` special `html` tags that `html2latex` cannot parse.
  1. `html2latex` takes `foo.html` and turns it into `foo.tex`
  1. Several more `sed` commands clean up `foo.tex` (these remove incorrect conversions by `html2tex`).


The `wikify.py` python script is a modified version of another Google Code project for converting wikis to html. (There are a few projects like this on the web, one is:http://chrisroos.co.uk/blog/2008-07-30-converting-google-code-wiki-content-to-html)

`html2latex` is a `C` program (likely constructed using `lex` and `yacc`). You can read more about these at: http://www.iwriteiam.nl/html2tex.html. The version in our manual converter is also slightly modified from the original.

`sed` is a useful Unix utility for editing files via regular expressions. (Wikipedia is a good place to begin reading about `sed`.)

Two bash scripts compose these utilities into one converter. `convert.sh` describes how to convert one file from `wiki` syntax to LaTeX. `wiki2latex` is a driver script that allows you to convert several `wiki` files to corresponding `latex` files.

## Usage ##
Using the manual converter is fairly simple. First, use `svn` to download a copy of our wikis:
```
svn checkout https://geocam.googlecode.com/svn/wiki/ geocam_wiki --username YOUR_USER_NAME
```
Within your wiki directory, you will see several files with the extension `.wiki`. Say you want to convert `foo.wiki`, `bar.wiki`, and `baz.wiki`. To do this, you can type (at the command line):
```
wiki2latex *.wiki
```
which makes a `.tex` file for each `.wiki` in the folder, or
```
wiki2latex foo.wiki bar.wiki baz.wiki
```
which produces just `foo.tex`, `bar.tex`, and `baz.tex`.

Obviously, you can modfy these scripts to add functionality to the converter (say, for example, you would like the script to intelligently convert and replace just recently updated wikis).

## Known Issues ##
The converter does not correctly parse wiki-links into links between TeX files. For the time being, these will have to be updated manually.