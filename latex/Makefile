# makefile for pdflatex
dm.pdf: rendu.tex
		pdflatex rendu.tex
		bibtex rendu
		pdflatex rendu.tex
		pdflatex rendu.tex

pdf: rendu.pdf
		xpdf rendu.pdf
