# java_game

## Sobre
Projeto de desenvolvimento de um jogo para praticar lógica de programação e programação orientada a objetos. As bibliotecas usadas foram Java AWT e Java Swing, bem como algumas classes para manipulação de arquivos .csv do Apache Commons.

O projeto não seguiu designs retrôs, nem estilo de jogo 2D. Por conta disso, o mapa não foi desenvolvido pelo padrão "Tiles", onde cada bloco, dispostos em matrize, são iterados para formar a imagem final. Nesse sentido, a imagem do nível já é carregada em sua totalidade, podendo-se usar artes prontas (nesse caso, pertencentes ao programa RPG MakerMZ).
Outro fator importante que essa escolha estética ocasionou foi a lógica de "hitbox". Para delimitar os hitbox do mapa foi seguido dois passos: 
1)  Com auxílio do gimp: contornar os objetos sólidos do mapa em questão com alguma cor não presente nele; 
2)  Dentro do APP: iterar cada pixel do mapa procurando e salvando, em uma estrutura de dados, a posição correspondente a tal cor, estabelecendo uma grade de 16x16 em toda imagem.

Segue abaixo algumas imagens do jogo, que por enquanto só possuí um nível.

<img src="/../readme/arquivos/image 1.png" width=655 height=400>
<img src="/../readme/arquivos/image 2.png" width=655 height=400>
<img src="/../readme/arquivos/image 3.png" width=655 height=400>
<img src="/../readme/arquivos/image 4.png" width=655 height=400>
