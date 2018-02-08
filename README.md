# Gizmoball

CS308 Semester 2 Group Project

**Gizmoball** is an arcade game very similar to pinball. The aim is to keep a MITBall moving around the playing area and not let it touch the bottom using different types of gizmos. 

## Docs

[Preliminary Design](docs/preliminarydesign/preliminarydesign.md)

## Build

Markdown to PDF

```
pandoc -s file.md -o output.pdf
```

Generate table of contents
```
npm install -g markdown-toc

markdown-toc -i file.md

pandoc preliminarydesign.md -o preliminarydesign.pdf --from markdown --template eisvogel --listings
```

## Group Members

Bence Sebestyen @xsb15143

Ioan Luca @xqb16141

Lyubomir Ivanov @kwb15150

Martin Kollie @vib15168

Niklavs Meiers @isb15151
