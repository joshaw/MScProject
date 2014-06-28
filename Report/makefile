# Created:  Sun 15 Jun 2014 04:30 pm
# Modified: Sat 28 Jun 2014 10:10 am

PROJECT=mainfile
TEX=pdflatex
OPTIONS=-file-line-error\
		-output-directory=$(DIR)\
		-halt-on-error
BIBTEX=bibtex -terse
DIR=build
BUILDTEX=latexmk -pdf -pdflatex="$(TEX)" $(OPTIONS) $(PROJECT).tex

CHECKDIR=mkdir -p $(DIR)

DIASRC=$(wildcard **/*.dia)
DIAPDF=$(subst .dia,.pdf,$(DIASRC))

all: $(DIAPDF) $(DIR)/$(PROJECT).pdf

%.pdf: %.dia
	echo $<
	dia -t svg -e "/dev/stdout" $<\
		| head -n-1 | rsvg-convert -f pdf > "$@"
	echo $@

$(DIR)/$(PROJECT).pdf: $(PROJECT).tex
	$(CHECKDIR)
	$(BUILDTEX)
	@xdotool search $(PROJECT).pdf key r > /dev/null 2>&1 || true

clean-all:
	find ${DIR}/ -type f -delete

clean:
	find ${DIR}/ -type f ! -name "*.pdf" -delete
# vim:ft=make
