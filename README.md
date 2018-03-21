# Gizmoball

![Logo](resources/images/icon.png)

CS308 Semester 2 Group Project

**Gizmoball** is an arcade game very similar to pinball. The aim is to keep a ball moving around the playing area and not let it touch the bottom using different types of gizmos. 

## Docs

[Preliminary Design](docs/preliminarydesign/preliminarydesign.md)

[Preliminary Release](docs/preliminaryrelease/preliminaryrelease.md)

[Testing Strategy](docs/preliminaryrelease/unit_test_strategy.md)

[Features](docs/features.md)


## IntelliJ Setup

Mark src as Source Root

Mark test as Test Root

Mark resources as Resources
 
Import physic-lib-1.0.jar from `/libs`

Import JUnit 5

## Build Documentation

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
