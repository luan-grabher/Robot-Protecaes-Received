package ContabilityTemplateImportation;

import Entity.Executavel;
import SimpleView.Loading;
import TemplateContabil.Model.Entity.Importation;
import TemplateContabil.Model.Entity.LctoTemplate;
import TemplateContabil.Model.ImportationModel;
import fileManager.FileManager;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import sql.Database;

public class Model {
    String sqlGetDocParticipant = FileManager.getText(".\\sql\\protecaesReceivedGetDocParticipant.sql");
    
    
    public class connectDatabase extends Executavel {
        @Override
        public void run() {
            File file = new File("\\\\zac\\robos\\Tarefas\\Arquivos\\sci.cfg");
            
            Database.setStaticObject(new Database(file));
            
            if(!Database.getDatabase().testConnection()){
                throw new Error("Erro ao conectar ao banco de dados.");
            }
        }
        
    }

    public class converterArquivoParaTemplate extends Executavel {

        private final Importation importation;
        private final Integer mes;
        private final Integer ano;

        public converterArquivoParaTemplate(Importation importation, Integer mes, Integer ano) {
            this.importation = importation;
            this.mes = mes;
            this.ano = ano;
        }
        

        @Override
        public void run() {
            //Chama o modelo da importação que irá criar o template e gerar warning se algo der errado
            ImportationModel modelo = new ImportationModel(importation.getNome(), mes, ano, importation, null);
            
            //Pega lctos
            List<LctoTemplate> lctos = importation.getLctos();
            
            //Loading
            Loading loading = new Loading("Buscando participantes", 0, lctos.size());
            
            //Percorre lctos para pegar
            for (LctoTemplate lcto : lctos) {
                //loading
                loading.next();
                
                if(lcto.getDocumento() != null && !"".equals(lcto.getDocumento())){                
                    Map<String,String> swaps =  new HashMap<>();
                    swaps.put("doc", lcto.getDocumento());


                    List<String[]> result = Database.getDatabase().select(sqlGetDocParticipant, swaps);

                    if(!result.isEmpty()){
                        lcto.setComplementoHistorico(lcto.getComplementoHistorico()  + " PARTICIPANTE " + result.get(0)[0]);
                    }
                }
            }
            
            //remove loading
            loading.dispose();
            
            modelo.criarTemplateDosLancamentos(importation);
        }
    }
}
