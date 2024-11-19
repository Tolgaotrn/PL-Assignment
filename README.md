/*
 * Implementar herança (FEITO)
 * Simplificar o processo de ediçao de utilizadores (FEITO)
 * Retirar o on delete cascade  (FEITO)
 * Adicionar a opção de adicionar um recurso, seja ele qual for (FEITO)
 * Resultados a aparecer com base na localização (FEITO)
 * Aplicar normas de rgpd às informações dos utilizadores e ao acesso que os administradores têm (FEITO)
 * Criptografar passwords dos utilizadores na base de dados (FEITO)
 * dividir o read por outras funções (FEITO)
 * retirar composição e adicionar herança entre recurso e os sub recursos (FEITO)
 * tabela de recursos ligar à tabela de favoritos através de uma relação 1:m, pois um recurso pode ser favorito de vários utilizadores (FEITO)
 * tabela de utilizadores ligar à tabela de favoritos através de uma relação 1:m, pois um utilizador pode ter vários favoritos (FEITO)
 * recursos 1:m----> favoritos <----1:m utilizadores (FEITO)
 * ADICIONAR FAVORITOS (FEITO)
 * ALTERAR NEXT PARA NEXTLINE, POIS OS INPUTS SE TIVEREM ESPÇOS NÃO FUNCIONAM (FEITO)
 * Acrescentar tabela tipo (id_tipo, tipo) e implementar herança para a tabela recursos (FEITO)
 * Acrescentar tabela localização (id_localizacao, cidade, distrito) e implementar herança para a tabela recursos (FEITO)
 * Acrescentar CRUD para a table Tipo
 */
Recursos API Client

        public void getRecursosTipoLocalizacao(int idtipo2, int idloc2) {
                System.out.println("Buscando recursos do tipo " + idtipo2 + " e localização " + idloc2);
        
                String url = BASE_URL + "/recursos/tipo/" + idtipo2 + "/localizacao/" + idloc2;
                RestTemplate restTemplate = new RestTemplate();

                try {
                    List<Recursos> recursos = restTemplate.exchange(url,HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Recursos>>() {}
                    ).getBody();

                    if (recursos != null && !recursos.isEmpty()) {
                        recursos.forEach(System.out::println);
                    } else {
                        System.out.println("Nenhum recurso encontrado para o tipo " + idtipo2 + " e localização " + idloc2);
                    }
                } catch (Exception e) {
                    System.err.println("Erro ao buscar recursos: " + e.getMessage());
                }
            }
