require 'fileutils'

class Deployer
		
		def deploy

			#Compile the projet with a system mvn clean install
			system("mvn clean install")

			sources=["adlogin.client/target/adlogin.client.war","adbase.server/target/adbase.server.war","adres.client/target/adres.client.war","adterm.server/target/adterm.server.war","adbase.client/target/adbase.client.war"]
			rbenv_version = ENV['JBOSS_HOME']
			dest = "#{rbenv_version}/standalone/deployments"

			#copy generated .war to the jboss deployement directory.
			sources.each do | filename |
				FileUtils.cp(filename,dest)
				puts "[SUCCESS ] Copied #{filename} to #{dest}"
			end
		end
end

class EmptyLinePrinter
	def gotoSingleLine
		puts ""
	end
	def gotoLine(number)
		if number <= 0 then
			number = 2
		end 
		for i in 1..number
			gotoSingleLine
		end
	end
end

emLinePrt = EmptyLinePrinter.new
d = Deployer.new();
emLinePrt.gotoLine(3)
puts "[INFO] Start deploying"
#execute the deploy
d.deploy
puts "[INFO] Finished deploying."