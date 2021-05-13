package ContabilityTemplateImportation;

import Entity.Executavel;
import TemplateContabil.Model.Entity.Importation;
import TemplateContabil.Model.Entity.LctoTemplate;
import TemplateContabil.Model.ImportationModel;
import java.io.File;
import java.util.List;
import sql.Database;

public class Model {
    
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
            
            //Percorre lctos para pegar
            for (LctoTemplate lcto : lctos) {
                
            }
            
            modelo.criarTemplateDosLancamentos(importation);
        }
    }
    
    /**/
    private static void defineParticipantCodes(ImportationModel importation){
        
        
        
    }
}
