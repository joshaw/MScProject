# Created:  Sun 15 Jun 2014 04:30 pm
# @author Josh Wainwright
# File name : makefile

PROJECT=mainfile
REFERENCES=references.bib
TEX=pdflatex
OPTIONS=-file-line-error\
		-output-directory=$(DIR)\
		-halt-on-error
BIBTEX=bibtex -terse
DIR=build
BUILDTEX=latexmk -pdf -pdflatex="$(TEX)" $(OPTIONS) $(PROJECT).tex

TEXFILES=$(wildcard **/*.tex)
SVGPDF=$(addprefix $(DIR)/,$(subst .svg,.pdf,$(wildcard **/*.svg)))
DIAPDF=$(addprefix $(DIR)/,$(subst .dia,.pdf,$(wildcard **/*.dia)))

all: $(DIR) $(DIR)/$(PROJECT).pdf $(SVGPDF) $(DIAPDF)
	cp "$(DIR)/$(PROJECT).pdf" "$(HOME)/web/host/disertation.pdf" || true
	cp "$(DIR)/$(PROJECT).pdf" "$(PROJECT).pdf"

$(DIR):
	mkdir $(DIR)
	echo $(DIAPDF)

.SECONDEXPANSION:
$(DIR)/%.pdf: %.dia
	echo "$< ==> $@"
	mkdir -p $(shell dirname $@)
	dia -t svg -e "/dev/stdout" $< 2> /dev/null | sed '/\/dev\/stdout/d'\
		| sed 's/CMU Serif/cmr10/' \
		| sed 's/CM Roman/cmr10/' \
		| rsvg-convert -f pdf > "$@"

$(DIR)/%.pdf: %.svg
	echo "$< ---> $@"
	mkdir -p $(shell dirname $@)
	sed -i 's/CMU Serif/cmr10/' "$<"
	sed -i 's/CM Roman/cmr10/' "$<"
	rsvg-convert -f pdf $< > "$@"

$(DIR)/$(PROJECT).pdf: $(PROJECT).tex $(REFERENCES) $(TEXFILES) $(DIAPDF) $(SVGPDF)
	$(BUILDTEX)
	@xdotool search $(PROJECT).pdf key r > /dev/null 2>&1 || true

once: $(PROJECT).tex
	$(TEX) $(OPTIONS) $(PROJECT).tex

open: all
	rifle $(DIR)/$(PROJECT).pdf

clean:
	rm -r $(DIR)

keep-pdf:
	find ${DIR}/ -type f ! -name "*.pdf" -delete

remove-pdf:
	find -name "$(PROJECT).pdf" -delete
# vim:ft=make
