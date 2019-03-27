## Pesquisa sobre Cluster

* Tipos e versões: cluster de alto desempenho, cluster de alta disponibilidade e cluster para balanceamento de carga

**Cluster de alto desempenho:** são direcionadas para aplicações bastantes exigentes no que diz respeito ao processamento, permitindo que a aplicação forneça resultados satisfatório em tempo hábil.
Podem ser usados para pesquisa científica onde normalmente se precisa analisar dados rapidamente e realizar cálculos complexos.

**Cluster de alta disponibilidade:** o foco está em manter a aplicação em pleno funcionamento. Não é aceitável que o sistema pare, mas se isso acontecer a paralisação deve ser a menor possível. Para atender a esta exigência, este modelo  trabalha com ferramentas de monitoramento, replicação de sistemas e computadores para substituição das máquinas que tiverem algum tipo de problema, além de, evidentemente, geradores de energia.

**Cluster para balanceamento de carga:** neste modelo de cluster as tarefas de processamento são distribuídas o mais uniformemente possível entre os nós. O foco é fazer com que cada computador receba e atenda uma requisição, e não necessariamente distribuir a tarefa entre as outras máquinas. Por exemplo, se um site na internet receba por volta de mil visitas por segundo e que um cluster formado por 20 nós tenha sido desenvolvido para atender a esta demanda. Como se trata de uma solução de balanceamento de carga, estas requisições são distribuídas igualmente entre as 20 máquinas, de forma que cada uma receba e realize, em média, 50 atendimentos a cada segundo.

* Como implementar:

**Exemplo de implementação do cluster no Hadoop Multi-Hosts**

Como teremos mais de um host, precisamos criar um mapa de rede e confirmar os endereços dos respectivos hosts.

Editar o arquivo hosts na pasta /etc/ para todos os hosts. Pode ser usado outros endereços privados para a configuração.

**vi /etc/hosts**
enter the following lines in the /etc/hosts file.

192.168.1.109 hadoop-master 
192.168.1.145 hadoop-slave-1 
192.168.56.1 hadoop-slave-2

**Configurações do Hadoop**

Abra os arquivos abaixo e edite com as seguintes configurações:

**core-site.xml**
``` <configuration>
   <property> 
      <name>fs.default.name</name> 
      <value>hdfs://hadoop-master:9000/</value> 
   </property> 
   <property> 
      <name>dfs.permissions</name> 
      <value>false</value> 
   </property> 
</configuration>
```

**hdfs-site.xml**
``` <configuration>
   <property> 
      <name>dfs.data.dir</name> 
      <value>/opt/hadoop/hadoop/dfs/name/data</value> 
      <final>true</final> 
   </property> 

   <property> 
      <name>dfs.name.dir</name> 
      <value>/opt/hadoop/hadoop/dfs/name</value> 
      <final>true</final> 
   </property> 

   <property> 
      <name>dfs.replication</name> 
      <value>1</value> 
   </property> 
</configuration>
```

**mapred-site.xml**
```<configuration>
   <property> 
      <name>mapred.job.tracker</name> 
      <value>hadoop-master:9001</value> 
   </property> 
</configuration>
```

hadoop-env.sh <br />
export JAVA_HOME=/opt/jdk1.7.0_17 <br />
export HADOOP_OPTS=-Djava.net.preferIPv4Stack=true <br />
export HADOOP_CONF_DIR=/opt/hadoop/hadoop/conf <br />

* Verificar a possibilidade de execução em máquina virtual: tem como implementar um cluster em uma máquina virtual.

