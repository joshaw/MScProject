# Created:  Sun 15 Jun 2014 04:30 pm
# Modified: Fri 11 Jul 2014 11:46 AM
# @author Josh Wainwright
# File name : makefile

PROJECT=mainfile
REFERENCES=references.bib
TEX=pdflatex
OPTIONS=-file-line-error\
		-silent\
		-output-directory=$(DIR)\
		-halt-on-error
BIBTEX=bibtex -terse
DIR=build
BUILDTEX=latexmk -pdf -pdflatex="$(TEX)" $(OPTIONS) $(PROJECT).tex

TEXFILES=$(wildcard **/*.tex)
# SVGSRC=$(wildcard **/*.svg)
SVGPDF=$(addprefix $(DIR)/,$(subst .svg,.pdf,$(wildcard **/*.svg)))
# DIASRC=$(wildcard **/*.dia)
DIAPDF=$(addprefix $(DIR)/,$(subst .dia,.pdf,$(wildcard **/*.dia)))

all: $(DIR) $(DIR)/$(PROJECT).pdf
	@echo $(SVGPDF)

$(DIR):
	mkdir $(DIR)
	echo $(DIAPDF)

.SECONDEXPANSION:
$(DIR)/%.pdf: %.dia
	echo "$< ---> $@"
	mkdir -p $(shell dirname $@)
	dia -t svg -e "/dev/stdout" $<\
		| head -n-1 | rsvg-convert -f pdf > "$@"

$(DIR)/%.pdf: %.svg
	echo "$< ---> $@"
	mkdir -p $(shell dirname $@)
	rsvg-convert -f pdf $< > "$@"

$(DIR)/$(PROJECT).pdf: $(PROJECT).tex $(REFERENCES) $(TEXFILES) $(DIAPDF) $(SVGPDF)
	$(BUILDTEX)
	@xdotool search $(PROJECT).pdf key r > /dev/null 2>&1 || true

open: all
	rifle $(DIR)/$(PROJECT).pdf

clean-all:
	rm -r $(DIR)

clean:
	find ${DIR}/ -type f ! -name "*.pdf" -delete
# vim:ft=make
